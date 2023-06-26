import Vue from 'vue';
import VueRouter from 'vue-router';

Vue.use(VueRouter);

import Login from '@/pages/Login'
import Room from '@/pages/Room'
import Register from '@/pages/Register'

export default new VueRouter({

	routes: [
		{
			path: '/',
			component: Login,
		},
		{
			path: '/login',
			component: Login,
		},
		{
			path: '/room',
			component: Room,
		},
        {
			path: '/register',
			component: Register,
		},
		
	]
})

