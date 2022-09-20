
let dataSave = require('../../deploy/deploy.json');

// export let AxiosURL = "http://localhost:5050"
export let AxiosURL = "https://back.codepony.top/"
export let Data = {
    web3:null,
    defaultAccount : dataSave.defaultAccount,
    address:dataSave.address,
    abi:dataSave.abi,
}

export let VoteParameters = {
    Title:'',
    BallotName:'',
}

export let CheckParameter = {
    Title:'',
}
export let MRoute = {
    initial:'/',
    signIn:'/sign_in',
    signUp:'/sign_up',
    user: {
        initial:'/user_space',
        vote:'/user_space/vote',
        voteProposals:'/user_space/vote_proposals',
        detailResult:'/user_space/vote_detail_result',
        result:'/user_space/vote_result',
        create:'/user_space/vote_create',
        voteInfo:'/user_space/vote_info',
        voteDetailInfo:'/user_space/voteDetailInfo',
    },

}
export let User = {
    ID:null,
    password:null,
    state: true, //表示可以登录
    checkState,
}
function checkState(){
    if(User.ID === null){
        return 0
    }
    else{
        return 1
    }
}
export let Admin = {
    admin:'admin',
    admin_password:'admin'
}
export default {Data,User,MRoute}