<template>
  <el-row>
    <el-col :span="24"><div class="table-position">
      <div class="text_style">
        投票结果
      </div>
      <el-table
          :data="tableData"
          style="width: 100%"
          :row-class-name="tableRowClassName"
          :header-cell-style="{
            background: '#96CDCD',
            color: '#333',
            fontWeight: '600',
            fontSize: '14px',
        }"
      >
        <el-table-column
            prop="rank"
            label="排名"
            width="100">
        </el-table-column>
        <el-table-column
            prop="name"
            label="被选人"
            width="300">
        </el-table-column>

        <el-table-column
            prop="poll"
            label="票数"
            width="300">
        </el-table-column>
      </el-table>
    </div></el-col>
  </el-row>
</template>




<script>

// import {Result} from "../javascript/voteResult";
import {AxiosURL, MRoute, User} from "../javascript/data";
//import Tool from "../javascript/tool";

export default {
  name: "VoteDetailResult",
  data() {
    let voteWho;
    return{
      voteWho,
      tableData: [
        {
          rank: '',
          name: '',
          poll: '',
        }
      ]
    }
  },
  methods: {
    tableRowClassName(row) {
      let _this = this
      // console.log(row.row.name)
      // console.log(_this.voteWho)
      if (row.row.name === _this.voteWho) {
        return 'success-row';
      }
      return ''
    },
    display(){
      let _this = this
      let  data = {id:User.ID,title:this.$route.params.title};
      // console.log("value:",data.title)
      this.axios({
        method: "post",
        url: AxiosURL + "/user_space/vote_result_check",
        data,
      }).then((res)=>{
        // console.log(res)
        for(let i = 0; i<res.data.proposal.length;i++){
          let table={}
          table.rank= res.data.proposal[i].rank+1;
          table.name= res.data.proposal[i].ballot;
          table.poll= res.data.proposal[i].vote_count;
          _this.tableData.splice(i,1,table)
        }
        _this.voteWho = res.data.voteWho;
        // console.log(_this.voteWho)
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

  }
}
</script>

<style scoped>
@import "../assets/table_style.css";
.text_style{
  text-align:center;
  background: #96CDCD;
  width:700px;
  font-size: 25px;
  font-family: 微软雅黑;
}
/*.el-table .warning-row {*/
/*  background: oldlace;*/
/*}*/

/*.el-table .success-row {*/
/*  background: #f0f9eb;*/
/*}*/
.table-position{
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 36px;
}
</style>