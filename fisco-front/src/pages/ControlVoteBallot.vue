<template>
  <el-row>
    <el-col :span="24"><div class="table-position">
      <table class="table-position" style="" >
        <tr>
          <td style="width: 200px;"></td>
          <td style="width: 250px;font-size: 25px ">投票详细信息</td>
          <td style="width: 75px;">
            <el-button
                size="mini"
                @click.native.prevent="resetVote()">Reset</el-button>
          </td>
        </tr>
      </table>
  <el-table
      :data="BallotInfo"
      style="width: 100%"
      :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }">

    <el-table-column
        prop="ballotRank"
        label="排名"
        width="100">
    </el-table-column>
    <el-table-column
        prop="voteBallot"
        label="参投项目"
        width="400">
    </el-table-column>
    <el-table-column
        prop="ticketsNums"
        label="票数"
        width="200">
    </el-table-column>
  </el-table>
    </div>
    </el-col>
  </el-row>
</template>

<script>
import {AxiosURL, CheckParameter, MRoute, User} from "@/javascript/data";

export default {
  name: "ControlVoteBallot",
  data(){
    return {
      proposalTitle:null,
      BallotInfo:[]
    }
  },
  methods:{
    display(){
      let _this = this
      let  data = {title:CheckParameter.Title};
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_detail_info",
        data,
      }).then((res)=>{
        // console.log(res)
        _this.proposalTitle = CheckParameter.Title;
        CheckParameter.Title = null
        for(let i = 0;i<res.data.length;i++){
          let table = {}
          table.ballotRank= res.data[i].rank;
          table.voteBallot= res.data[i].title;
          table.ticketsNums= res.data[i].vote_count;
          _this.BallotInfo.splice(i,1,table)
        }
      })
    },
    resetVote(){
      let _this = this
      let  data = {title:_this.proposalTitle,id:User.ID};
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_reset",
        data,
      }).then((res)=>{
        let _this = this
        console.log(res)
        if(res.data === 1){
          _this.infoAlert('','投票项目删除成功');
          _this.$router.push(MRoute.user.voteInfo)
        }

      })
    },
    infoAlert(message,title){
      let _this = this
      _this.$info.alert(message, title, {
        confirmButtonText: '确定',
      });
    },
  },
  mounted() {
    let _this = this
    // Vote.blockInit()
    _this.display()
  }
}
</script>

<style scoped>
/*.text_style{*/
/*  text-align:center;*/
/*  background: #96CDCD;*/
/*  width:700px;*/
/*  font-size: 25px;*/
/*  font-family: 微软雅黑;*/
/*}*/

.table-position{
  margin:0 auto;
  width:700px;
  /*border-radius: 4px;*/
  min-height: 36px;
  background-color:#96CDCD
}
</style>