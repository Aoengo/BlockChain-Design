<template>
<!--  <el-table-->
<!--      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"-->
<!--      style="width: 100%">-->
  <el-row>
    <el-col  :span="24"><div class="table-position">
      <div class="text_style">
        投票
      </div>
    <el-table :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
              style="width: 100%"
              highlight-current-row
              :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }">
      <el-table-column
          label="标题"
          prop="title">
      </el-table-column>
      <el-table-column
          label="截止时间"
          prop="time"
          align="center">
      </el-table-column>

      <el-table-column
        align="right">
      <template slot="header" slot-scope="scope">
        <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"/>
      </template>
      <template slot-scope="scope">
        <el-button
            size="mini"
            type="primary"
            plain
            @click="voteHandle(scope.row.title)">进入投票</el-button>
<!--        <div v-if="type === 'true'">-->
<!--        <el-button-->
<!--            size="mini"-->
<!--            type="danger"-->
<!--            @click="deleteHandle(scope.$index, scope.row)">Delete</el-button>-->
<!--        </div>-->
      </template>
    </el-table-column>
  </el-table>
    </div>
    </el-col>
  </el-row>
</template>

<script>
import {AxiosURL, MRoute, VoteParameters} from "@/javascript/data";

export default {
  name: "VoteProposals",
  data() {
    return {
      tableData: [],
      search: '',
      type:'',
    }
  },
  methods:{
    voteHandle(name){
      VoteParameters.Title = name;
      // console.log("here",VoteParameters.Title )
      this.$router.push(MRoute.user.vote)
    },
    deleteHandle(){

    },
    display(){
      let _this = this
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_proposals",
      }).then((res)=>{
        console.log(res)
        for(let i = 0;i < res.data.value1.length;i++){
          let table = {}
          table.title = res.data.value1[i]
          table.time = _this.dateProcess(res.data.value2[i])
          _this.tableData.splice(i,1,table)
        }
         // console.log(res);
      })
    },
    dateProcess(value){
      let date = new Date(parseInt(value));
      // console.log(date)
      let year = date.getFullYear();
      let month= date.getMonth()+1;
      month = month < 10 ? ('0' + month) : month;
      let day  = date.getDate();
      day = day < 10 ? ('0' + day) : day;
      let hours = date.getHours();
      hours = hours < 10 ? ('0' + hours) : hours;
      return year + "-" + month + "-" + day + "-" + hours;
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