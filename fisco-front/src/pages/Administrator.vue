<template>
  <el-row>
    <el-col :span="24"><div class="grid-content">
      <div class="text_style">
        管理员后台
      </div>

      <table class="table-position" style="" >
        <tr>
          <div class="button_position">
            <el-button type="success" style="width:200px" v-on:click="create_vote">新建投票项目</el-button>
            <el-button type="success" style="width:200px" v-on:click="check_info">查看项目详细信息</el-button>
            <el-button type="warning" style="width:200px" v-on:click="check_result">查看投票结果</el-button>
          </div>
        </tr>
        <tr>
          <div class="button_position">
            <el-button type="success" style="width:100px" v-on:click="logout">注销</el-button>
            <el-button type="danger" style="width:100px" v-on:click="go_back">返回主页</el-button>
          </div>
        </tr>
      </table>
    </div>
      <router-view></router-view>
    </el-col>
  </el-row>
</template>

<script>
import {User} from "../javascript/data"
import {MRoute} from "../javascript/data";

export default {
  name: "Administrator",
  methods:{
    go_back(){
      User.state = true
      this.$router.push(MRoute.initial)
    },
    logout(){
      User.state = true
      this.$router.push(MRoute.initial)
    },
    create_vote(){
      this.$router.push(MRoute.admin.create)
    },
    check_info(){
      this.$router.push(MRoute.admin.voteInfo)
    },
    vote(){
      this.$router.push(MRoute.admin.vote)
    },
    check_result() {
      this.$router.push(MRoute.admin.result)
    }
  },
  mounted() {

  }
}
</script>

<style scoped>
.button_position{
  text-align:center;
}
.text_style{
  text-align:center;
  font-size: 25px;
  font-family: 微软雅黑;
}
.table-position{
  margin:0 auto;
  width:700px;
  min-height: 120px;
  background-color:#99a9bf
}
.grid-content {
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 150px;
  background: #99a9bf;
}
</style>