//引入Vue
import Vue from 'vue'
//引入App
import App from './App.vue'

import axios from 'axios'
import VueAxios from 'vue-axios'
//引入VueRouter
import VueRouter from 'vue-router'
//引入路由器
import router from './router'

import { MessageBox,Popover,Tag,Button,Select ,Input,Container,Divider,Table,TableColumn ,Row,Col,Header,Alert,DatePicker,TimeSelect} from 'element-ui'





//console.log(votingInstance)
//关闭Vue的生产提示
Vue.config.productionTip = false

//应用插件
Vue.use(VueAxios, axios)
//axios.defaults.withCredentials = false;
Vue.use(TimeSelect)
Vue.use(DatePicker)
Vue.use(VueRouter)
Vue.use(Container)
Vue.use(Popover)
Vue.use(Tag)
Vue.use(Button)
Vue.use(Select)
Vue.use(Input)
Vue.use(Divider)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Row)
Vue.use(Col)
Vue.use(Header)
Vue.use(Alert)
Vue.prototype.$info = MessageBox
//创建vm
new Vue({
    el:'#app',
    render: h => h(App),
    router:router
})

//解决router重复点击报错问题
const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}