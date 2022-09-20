<template>
  <el-row>
    <el-col :span="24"><div class="table-position">
      <div class="text_style">
        投票结果
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
            prop="voteBallot"
            label="被选人"
            width="200">
        </el-table-column>
        <el-table-column
            prop="voteState"
            label="存在状态"
            width="100">
          <template slot-scope="scope">

            <el-popover trigger="hover" placement="top">
              <p style="font-size: 12px">发起人可能已将该投票删除</p>
              <div v-if="scope.row.voteState === '异常'" slot="reference" class="name-wrapper">
                <el-tag type="danger" size="medium">{{ scope.row.voteState }}</el-tag>
              </div>
            </el-popover>
            <div v-if="scope.row.voteState === '正常'" slot="reference" class="name-wrapper">
              <el-tag type="success" size="medium">{{ scope.row.voteState }}</el-tag>
            </div>
          </template>

        </el-table-column>
        <el-table-column
            label="操作"
            width="100">
          <template slot-scope="scope">
            <div v-if="scope.row.voteState === '正常'" slot="reference" class="name-wrapper">
              <el-button
                  size="mini"
                  type="success"
                  @click="_check(scope.row.voteTitle)">查看</el-button>
            </div>
            <div v-if="scope.row.voteState === '异常'" slot="reference" class="name-wrapper">
              <el-button
                  size="mini"
                  type="danger"
                  @click="_delete(scope.row.voteTitle)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </div>
    </el-col>
  </el-row>
</template>




<script>

// import {Result} from "../javascript/voteResult";
import {AxiosURL, MRoute, User, VoteParameters} from "../javascript/data";
//import Tool from "../javascript/tool";
export default {
  name: "VoteResult",
  data() {
    return {
      ProposalInfo: []
    }
  },
  methods: {
    _delete(title){
      console.log(title)
      let _this = this
      let  data = {id:User.ID,title:title};
      // console.log(data)
      //发送 POST 请求
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_result_delete",
        data,
      }).then((res)=>{
        console.log(res.data)
        if(res.data === 0xfffe){
          _this.infoAlert('','删除成功');
        }else {
          _this.infoAlert('','删除失败');
        }
        // _this.display()
      })
    },
    _check(title){
      this.$router.push(
          {
            path:MRoute.user.detailResult,
            name:'detail_result',
            params: {
              title:title
            }
      }
      )
    },
    display(){
      let _this = this
      let  data = {id:User.ID};
      // console.log(data)
      //发送 POST 请求
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_result",
        data,
      }).then((res)=>{
        // console.log(res.data)
        for(let i = 0; i<res.data.length;i++){
          let table={}
          table.voteTitle= res.data[i].title;
          table.voteBallot= res.data[i].ballot;
          if(res.data[i].state === -0xfffc){
            table.voteState="正常"
          }else{
            table.voteState="异常"
          }

          _this.ProposalInfo.splice(i,1,table)
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
    _this.display();
    // Result.blockInit()
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