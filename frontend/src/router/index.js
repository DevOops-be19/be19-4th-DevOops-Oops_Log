import { createRouter, createWebHistory } from 'vue-router'


// Imports 각각 위치 맞춰서

//common
import MainView from '../components/pages/MainView.vue';
import AboutView from '../components/pages/AboutView.vue';
import ContactView from '../components/pages/ContactView.vue';
import NoticeView from '../components/pages/NoticeView.vue';

// 형석님

// 지현님

// 건일님

// 서진님

// 순우님

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/',
      redirect: '/main'
    },
    { 
      path: '/main',
      name: 'Main',
      component: MainView
    },
    { 
      path: '/about',
      name: 'About',
      component: AboutView
    },
    { 
      path: '/contact',
      name: 'Contact',
      component: ContactView
    },
    { 
      path: '/notice',
      name: 'Notice',
      component: NoticeView
    },


    // { 
    //   path: '/',
    //   name: '',
    //   component: 
    // },
],
})

export default router
