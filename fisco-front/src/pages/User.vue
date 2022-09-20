<template>
<el-row>
  <el-col :span="24"><div class="grid-content">
    <div class="text_style">
      个人空间
    </div>
<!--      <div class="button_position">-->
<!--        <el-button type="danger" style="width:100px" v-on:click="go_back">返回主页</el-button>-->
<!--        <el-button type="success" style="width:100px" v-on:click="logout">注销</el-button>-->
<!--        <el-button type="warning" style="width:100px" v-on:click="vote">投票</el-button>-->
<!--        <el-button type="warning" style="width:200px" v-on:click="check_result">查看投票结果</el-button>-->
<!--      </div>-->
    <table class="table-position" style="" >
      <tr>
        <div class="button_position">
          <el-button type="success" style="width:200px" v-on:click="create_vote">新建投票项目</el-button>
          <el-button type="success" style="width:200px" v-on:click="check_info">个人项目详细信息</el-button>
          <el-button type="warning" style="width:200px" v-on:click="check_result">查看参与投票结果</el-button>
        </div>
      </tr>
      <tr>
        <div class="button_position">
          <el-button type="warning" style="width:200px" v-on:click="voteProposals">投票</el-button>
          <el-button type="success" style="width:200px" v-on:click="logout">注销</el-button>
          <el-button type="danger" style="width:200px" v-on:click="go_back">返回主页</el-button>
        </div>
      </tr>
    </table>
    </div>
    <router-view></router-view>
  </el-col>
</el-row>
</template>

<script>
import {MRoute, User} from "@/javascript/data"
export default {
  name: "User",
  data(){
    return {
      User
    }
  },
  methods:{
    create_vote(){
      this.$router.push(MRoute.user.create)
    },
    check_info(){
      this.$router.push(MRoute.user.voteInfo)
    },
    go_back(){
      this.$router.push(MRoute.initial)
    },
    logout(){
      User.ID = ''
      User.password = ''
      User.state = true
      this.$router.push(MRoute.initial)
    },
    voteProposals(){
      this.$router.push(MRoute.user.voteProposals)
    },
    check_result() {
      this.$router.push(MRoute.user.result)
    },

    checkState(){
      let _this = this
      if(User.state !== false){
        _this.infoAlert('身份信息过期','进入个人空间失败')
        _this.$router.push(MRoute.initial)
      }
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
    _this.checkState()
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
  background-color:#96CDCD
}
.grid-content {
  margin:0 auto;
  width:700px;
  border-radius:4px 4px 0 0 ;
  min-height: 100px;
  background: #96CDCD;
}
</style>