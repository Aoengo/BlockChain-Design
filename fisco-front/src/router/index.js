// 该文件专门用于创建整个应用的路由器
import VueRouter from 'vue-router'
//引入组件
import {MRoute} from "../javascript/data";
import SignIn from '../pages/SignIn'
import SignUp from '../pages/SignUp'
import InitPage from '../pages/InitPage'
import VoteResult from "../pages/VoteResult";
import User from "../pages/User";
import Vote from "../pages/Vote";
import CreateVote from "../pages/CreateVote";
import Administrator from  "../pages/Administrator"
import ControlVote from "../pages/ControlVote";
import ControlVoteBallot from "../pages/ControlVoteBallot";
import VoteProposals from "@/pages/VoteProposals";
import VoteDetailResult from "@/pages/VoteDetailResult";

//创建并暴露一个路由器
export default new VueRouter({
    routes:[
        {path:MRoute.initial,component:InitPage},
        {path:MRoute.signIn,component:SignIn},
        {path:MRoute.signUp,component:SignUp},

        {
            path:MRoute.user.initial,
            component:User,
            children:[
                {path:MRoute.user.vote,component:Vote},
                {path:MRoute.user.voteProposals,component:VoteProposals},
                {path:MRoute.user.result,name:'result',component:VoteResult},
                {path:MRoute.user.detailResult,name:'detail_result',component:VoteDetailResult},
                {path:MRoute.user.create,component:CreateVote},
                {path:MRoute.user.voteInfo,component:ControlVote},
                {path:MRoute.user.voteDetailInfo,component:ControlVoteBallot},
            ]
        },
    ]
})
