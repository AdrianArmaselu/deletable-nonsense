pragma solidity ^0.4.10;


contract Competition {

    struct Participant {
    int trials;
    // Submission stats, kept for proof purposes
    uint reps;
    uint duration;
    bool isWinner;
    }

    address public owner;

    address public currentWinner;

    uint public bestScoreReps;

    uint public bestScoreDuration;

    uint public reward;

    int public numberOfTrials;

    bool public isStarted;

    bool public isFinished;

    mapping (address => Participant) participants;

    function Competition(uint _reward, int _numberOfTrials){
        owner = msg.sender;
        reward = _reward;
        numberOfTrials = _numberOfTrials;
        isStarted = true;
    }

    function stopCompetition(){
        require(msg.sender == owner && isStarted);
        isFinished = true;
    }

    function joinCompetition(){
        require(participants[msg.sender].trials == 0);
        participants[msg.sender].trials = numberOfTrials;
    }

    function makeSubmission(uint reps, uint duration){
        require(participants[msg.sender].trials > 0 && isStarted && !isFinished);
        participants[msg.sender].trials -= 1;
        participants[msg.sender].reps = reps;
        participants[msg.sender].duration = duration;

        // this participant cannot participate any more
        if (participants[msg.sender].trials == 0) {
            participants[msg.sender].trials = -1;
        }

        // this is horrible code naming
        uint r1d2 = bestScoreReps * duration;
        uint r2d1 = reps * bestScoreDuration;

        if (r1d2 < r2d1) {
            bestScoreReps = reps;
            bestScoreDuration = duration;
            participants[currentWinner].isWinner = false;
            participants[msg.sender].isWinner = true;
            currentWinner = msg.sender;
        }
    }

    function claimReward(){
        require(isFinished && participants[msg.sender].isWinner);
        msg.sender.transfer(reward);
    }
}
