<template>
    <div id="app">
      <BanNer></BanNer>
      <router-view></router-view>
    </div>
</template>


<script>

import BanNer from './components/BanNer'

export default {
  name:'App',
  components:{
    BanNer,
  },
}
</script>

<style>

#app {
  font-family: "微软雅黑";
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  margin-top: 20px;
  /*background-image:url("picture/back_ground.jpg") ;*/
}
</style>