// import { NgModule } from '@angular/core';
// import { RouterModule, Routes } from '@angular/router';
//
//
// const routes: Routes = [
//   {
//     path: '',
//     redirectTo: 'pages/index',
//     pathMatch: 'full'
//   },
//   { path: '**', redirectTo: 'pages/index'}
// ];
//
// @NgModule({
//   imports: [
//     RouterModule.forRoot(routes)
//   ],
//   exports: [
//     RouterModule
//   ],
//   declarations: []
// })
// export class AppRouting { }
import { Routes, RouterModule } from '@angular/router';

const appRoutes: Routes = [
  {
    path: '',
    redirectTo: 'pages/login',
    pathMatch: 'full'
  },
  {
    path: ':company',
    redirectTo: 'pages/:company'
  },
  {
    path: '**',
    redirectTo: 'pages/home-page'
  }
];

export const APP_ROUTING = RouterModule.forRoot(appRoutes);

