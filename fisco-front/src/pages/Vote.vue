<template>
  <el-row>
  <el-col  :span="24"><div class="table-position">
    <div class="text_style">
      投票
    </div>
    <el-table :data="ballotList.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
              style="width: 100%"
              highlight-current-row
              :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }">
      <el-table-column
          label="选项"
          prop="name">
      </el-table-column>
      <el-table-column
          align="right">
        <template slot="header" slot-scope="{}">
          <el-input
              v-model="search"
              size="mini"
              placeholder="输入关键字搜索"/>
        </template>
        <template slot-scope="scope">
          <div v-if="voteState === true">
            <el-button
                type="primary"
                size="mini"
                @click="vote(scope.row.name)">投票</el-button>
          </div>
          <div v-else-if="voteState === false">
            <el-button
                type="primary"
                size="mini"
                disabled
                @click="vote(scope.row.name)">投票</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
  </el-col>
  </el-row>
</template>

<script>
// import {Vote} from "../javascript/vote";
import {AxiosURL, MRoute, User, VoteParameters} from "@/javascript/data";
export default {
  name: "Vote",
  data() {
    let search;
    let voteState;
    return {
      ballotList: [],
      search,
      voteState,
    }
  },

  methods: {
    vote(name){
      let _this = this
      VoteParameters.BallotName = name;
      let  data = {title:VoteParameters.Title,name:VoteParameters.BallotName,id:User.ID};
      // console.log(data)
      //发送 POST 请求
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_chose",
        data,
      }).then((res)=>{
        // console.log(res.data)
        if(res.data !== 0xffff){
          _this.infoAlert('','投票成功')
        }else{
          _this.infoAlert('每个项目每人只能投一次,同时你可能已经超时','投票失败')
        }
      })
    },
    getVoteState() {
      let _this = this
      let  data = {title:VoteParameters.Title,id:User.ID};
      // console.log(data)
      //发送 POST 请求
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/user_vote_state",
        data,
        }).then((res)=>{
          // console.log(res.data)
          _this.voteState = res.data === true;
      })
    },
    infoAlert(message,title){
      let _this = this
      _this.$info.alert(message, title, {
        confirmButtonText: '确定',
      });
    },
    display(){
      let _this = this
      let  data = {title:VoteParameters.Title};
      // console.log(data)
      //发送 POST 请求
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote",
        data,
      }).then((res)=>{
        // console.log(res)
        for(let i = 0; i< res.data.length;i++){
          let table = {}
          table.name = res.data[i]
          _this.ballotList.splice(i,1,table)
        }
      })
    },
  },
  mounted() {
    let _this = this
    // Vote.blockInit()
    _this.display()
    _this.getVoteState()

    // console.log(User.checkState())
  }
}
</script>
<style scoped>

.text_style{
  text-align:center;
  font-size: 25px;
  font-family: 微软雅黑;
}

.table-position{
  margin:0 auto;
  width:700px;
  min-height: 36px;
  background: #96CDCD;
}


</style>