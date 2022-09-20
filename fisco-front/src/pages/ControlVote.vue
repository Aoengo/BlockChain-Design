<template>
  <el-row>
    <el-col :span="24"><div class="table-position">
      <div class="text_style">
        投票项目信息
      </div>
      <el-table
          :data="ProposalInfo"
          style="width: 100%"
          :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }">
        <el-table-column
            prop="voteTitle"
            label="投票项目"
            width="300">
        </el-table-column>
        <el-table-column
            prop="voterNums"
            label="参与人数"
            width="200">
        </el-table-column>
        <el-table-column
            align="right">
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="check(scope.row.voteTitle)">查看</el-button>
        </template>
        </el-table-column>

      </el-table>


    </div>
    </el-col>
  </el-row>
</template>

<script>

// import {Result} from "../javascript/voteResult";
// import {Admin,User} from "../javascript/data";
// import {Control} from "../javascript/controlVote";
// import Tool from "../javascript/tool";

import {AxiosURL, CheckParameter, User} from "@/javascript/data";

const {MRoute} = require("../javascript/data");
export default {
  name: "ControlVote",
  data() {
    return {
      ProposalInfo:[],
    }
  },
  methods: {
    getProposalInfo(){
      let _this = this
      let  data = {ID:User.ID};
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_info",
        data,
        // headers:
        //   {
        //     'Content-Type': 'application/json'
        //   }
      }).then((res)=>{
        for(let i = 0;i<res.data.length;i++){
          let table = {}
          table.voteTitle= res.data[i].title;
          table.voterNums= res.data[i].nums;
          _this.ProposalInfo.splice(i,1,table)
        }
      })
    },
    check(title){
      console.log(title)
      let _this = this
      CheckParameter.Title = title
      _this.$router.push(MRoute.user.voteDetailInfo)
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
    _this.getProposalInfo()

  }
}
</script>

<style scoped>
.text_style{
  text-align:center;
  background: #96CDCD;
  width:700px;
  font-size: 25px;
  font-family: 微软雅黑;
}

.table-position{
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 36px;
}
</style>