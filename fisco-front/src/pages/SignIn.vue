<template>
  <el-row>
    <el-col :span="24">
      <div class="grid-content">
        <div class="text_style">
          登录
        </div>
        <table class="table-position">
          <tr>
            <td>请输入您的手机号:</td>
            <td>
              <el-input class="input-style" v-model="ID" type="text" placeholder="Input your Name..."></el-input>
            </td>
          </tr>
          <tr>
            <td>请输入您的密码:</td>
            <td><el-input class="input-style" v-model="password" type="password" placeholder="Input your Password..."></el-input></td>
          </tr>
        </table>
        <el-divider></el-divider>
        <div class="button_position">
          <el-button type="danger" style="width:100px" v-on:click="go_back">返回</el-button>
          <el-button type="success" style="width:100px" v-on:click="login">登录</el-button>
        </div>
      </div>
    </el-col>
  </el-row>

</template>

<script>
	// import {SignIn} from "../javascript/signIn";
  import {AxiosURL, MRoute, User} from "@/javascript/data"
  export default {
		name:'SignIn',
    data() {
      return {
        ID:null,
        password:null,
      }
    },
    methods:{
      go_back(){
        this.$router.replace('/')
      },
      login(){
        let _this = this
        if(User.state){
          let  data = {username:_this.ID,password:_this.password};
          //发送 POST 请求
          this.axios({
            method: "post",
            url: AxiosURL + "/sign_in",
            data,
          }).then((res) =>{
            console.log(res.data)
            if(res.data === true && User.state === true){
              console.log("login success")
              _this.infoAlert('','登录成功')
              User.ID = _this.ID
              User.password = _this.password
              User.state = false
              _this.$router.push(MRoute.user.initial)
            }
            else if (res.data === true && User.state === false){
              _this.infoAlert('您当前已登录','登录失败')
            }
            else if(res.data === false){
              _this.infoAlert('请重试','登录失败')
            }
          });
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
      // SignIn.blockInit()
    }
  }
</script>

<style scoped>
.button_position{
  text-align:center;
}
.table-position{
  margin:0 auto;
}
.text_style{
  text-align:center;
  font-size: 25px;
  font-family: 微软雅黑;
}
.input-style{
  width: 500px;
}
.grid-content {
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 250px;
  background: #96CDCD;
}
</style>