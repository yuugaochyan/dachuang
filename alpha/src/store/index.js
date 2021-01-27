import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userID:JSON.parse(localStorage.getItem("userID")),
    token:JSON.parse(localStorage.getItem("token"))
  },
  mutations: {
    setuserID(state,v) {
      localStorage.setItem('userID',JSON.stringify(v));
      state.userInfo = v;
    },
    setToken(state,v) {
      localStorage.setItem('token',JSON.stringify(v));
      state.token = v;
    }
  },
  actions: {
  },
  modules: {
  }
})
