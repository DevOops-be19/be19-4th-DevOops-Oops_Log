import { createRouter, createWebHistory } from 'vue-router'


// Imports 각각 위치 맞춰서

//common
import MainView from '../components/view/MainView.vue';
import AboutView from '../components/view/AboutView.vue';
import ContactView from '../components/view/ContactView.vue';

// 형석님
import NoticeView from '../components/view/NoticeView.vue';
import NoticeRegistView from '../components/view/NoticeRegistView.vue';
import NoticeEditView from '../components/view/NoticeEditView.vue';
import OopsView from '../components/view/OopsView.vue'
import OopsEditView from '../components/view/OopsEditView.vue';
import OopsRegistView from '../components/view/OopsRegistView.vue';
import OohView from '../components/view/OohView.vue'
import OohEditView from '../components/view/OohEditView.vue';
import OohRegistView from '../components/view/OohRegistView.vue';


// 지현님
import LoginView from '@/components/view/LoginView.vue';

// 건일님
import AdminMember from '@/components/admin/AdminMember.vue';
import AdminReport from '@/components/admin/AdminReport.vue';
import AdminTag from '@/components/admin/AdminTag.vue';
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
  component: NoticeView, // 공지 리스트 or 레이아웃
  children: [
    { path: 'insert', name: 'InsertNotice', component: NoticeRegistView},
    { path: 'update/:id', name: 'UpdateNotice', component: NoticeEditView}
  ]
},
    { 
      path: '/oops',
      name: 'Oops',
      component: OopsView,
      children: [
        { path: 'insert', name: 'InsertOops', component: OopsRegistView},
        { path: 'update/:id', name: 'UpdateOops', component: OopsEditView}
      ]
    }, 
    { 
      path: '/ooh',
      name: 'Ooh',
      component: OohView,
      children: [
        { path: 'insert', name: 'InsertOoh', component: OohRegistView},
        { path: 'update/:id', name: 'UpdateOoh', component: OohEditView}
      ]
    }, 


   // router/index.js
    {
      path: '/mypage',
      component: () => import('../components/view/MyPageView.vue'),
      redirect: { name: 'mypage-info' },
      children: [
        { path: 'info',      name:'mypage-info',      component: () => import('../components/mypage/InfoTab.vue'),      meta:{label:'개인정보 수정'} },
        { path: 'oops',      name:'mypage-oops',      component: () => import('../components/mypage/OopsTab.vue'),      meta:{label:'Oops 기록'} },
        { path: 'ooh',       name:'mypage-ooh',       component: () => import('../components/mypage/OohTab.vue'),       meta:{label:'Ooh 기록'} },
        { path: 'growth',    name:'mypage-growth',    component: () => import('../components/mypage/GrowthTab.vue'),    meta:{label:'성장 그래프', keepAlive:true} },
        { path: 'reco',      name:'mypage-reco',      component: () => import('../components/mypage/RecommendTab.vue'), meta:{label:'추천게시글'} },
        { path: 'bookmarks', name:'mypage-bookmarks', component: () => import('../components/mypage/BookmarksTab.vue'), meta:{label:'북마크기록'} },
        { path: 'followers', name:'mypage-followers', component: () => import('../components/mypage/FollowersTab.vue'), meta:{label:'팔로우기록'} },
        { path: 'settings',  name:'mypage-settings',  component: () => import('../components/mypage/SettingsTab.vue'),  meta:{label:'설정'} },
      ]
    },


    {
      path: '/admin',
      component: () => import('@/components/view/AdminLayout.vue'),
      meta: { isAdmin: true },
      children: [
        { path: '', redirect: '/admin/member'},  
        { path: 'member', component: AdminMember },
        { path: 'report', component: AdminReport },
        { path: 'tag', component: AdminTag}
      ]
    },
    { 
      path: '/login',
      name: 'Login',
      component: LoginView
    },

    // { 
    //   path: '/',
    //   name: '',
    //   component: 
    // },
],
})

export default router
