export let AxiosURL = "http://localhost:5050"
// export let AxiosURL = "https://back.codepony.top/"



export let UserDate={
  date : new Date(),
  years : [],
  months : [],
  days : [],
  hours : [],
  
}

export let User = {
  ID:null,
  password:null,
  state: true, //表示可以登录
  checkState,
}
function checkState(){
  if(User.ID === null){
      return 0
  }
  else{
      return 1
  }
}

//获取年
for (let i = UserDate.date.getFullYear(); i <= UserDate.date.getFullYear() + 5; i++) {
  UserDate.years.push("" + i);
}
//获取月份
for (let i = 1; i <= 12; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  UserDate.months.push("" + i);
}
//获取日期
for (let i = 1; i <= 31; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  UserDate.days.push("" + i);
}
//获取小时
for (let i = 0; i < 24; i++) {
  if (i < 10) {
    i = "0" + i;
  }
  UserDate.hours.push("" + i);
}