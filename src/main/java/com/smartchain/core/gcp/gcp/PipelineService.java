package com.smartchain.core.gcp.gcp;

import com.smartchain.core.gcp.io.ResourceManager;
import com.smartchain.core.gcp.model.GoogleCloudConfiguration;
import org.apache.beam.runners.dataflow.DataflowRunner;
import org.apache.beam.runners.dataflow.options.DataflowPipelineOptions;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;

import java.io.IOException;

/**
 *
 * This service is used for transforming data to cloud storage
 *
 * To use local files (local files in a google cloud server), use the following format: "src/main/resources/dataproc.json".
 * Do not use absolute file paths because it will fail.
 *
 */
public class PipelineService extends GoogleCloudService{

    public PipelineService(GoogleCloudConfiguration configuration) {
        super(configuration);
    }

    public void submitJob() throws IOException {
        Pipeline pipeline = Pipeline.create(getPipelineOptions());
        pipeline.apply(TextIO.read().from("src/main/resources/dataproc.json"))
                .apply(new ComputeWordLengths())
                .apply(TextIO.write().to("gs://dataflow-alysia-172200/dataproc.txt"));
        pipeline.run().waitUntilFinish();
    }

    private PipelineOptions getPipelineOptions() throws IOException {
        DataflowPipelineOptions dataflowPipelineOptions = PipelineOptionsFactory.as(DataflowPipelineOptions.class);
        dataflowPipelineOptions.setProject(configuration.getProjectId());
        dataflowPipelineOptions.setRunner(DataflowRunner.class);
        dataflowPipelineOptions.setGcpCredential(ResourceManager.getServiceAccountApiKeyCredentials());
        return dataflowPipelineOptions;
    }

    class ComputeWordLengths extends PTransform<PCollection<String>, PCollection<String>> {
        @Override
        public PCollection<String> expand(PCollection<String> input) {
            return input;
        }
    }
}
