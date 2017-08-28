/**
 * Created by adrian on 8/25/17.
 */
const Web3 = require('web3')

let web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"))

let account = '0xE767aEB31dAAF66366999F72FB5De2CEEA76c277'.toLowerCase()
let filter = web3.eth.filter('latest')
filter.watch(function(error, result) {
    if (!error) {
        let confirmedBlock12 = web3.eth.getBlock(web3.eth.blockNumber - 11)
        confirmedBlock12.transactions.forEach(function(txId) {
            let transaction = web3.eth.getTransaction(txId)
            if (transaction.to == account) {
                // Do something useful (eg send a message)
                console.log(transaction.from)
            }
        })
    }
})