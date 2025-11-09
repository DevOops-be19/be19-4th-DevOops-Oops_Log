import AdminMember from '@/views/admin/components_1/AdminMember.vue';
import AdminReport from '@/views/admin/components_1/AdminReport.vue';
import AdminTag from '@/views/admin/components_1/AdminTag.vue';


export default [
    {
        path: '/admin',
        component: () => import('@/views/admin/AdminLayout.vue'),
        meta: { isAdmin: true },
        children: [
          { path: '', redirect: '/admin/member'},  
          { path: 'member', component: AdminMember },
          { path: 'report', component: AdminReport },
          { path: 'tag', component: AdminTag}
        ]
    }
    ]