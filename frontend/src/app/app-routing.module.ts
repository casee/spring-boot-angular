import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AngularDemoComponent } from './angular-demo/angular-demo.component';
import { AboutComponent } from './about/about.component';
import { ActionPageComponent } from './action-page/action-page.component';
import { AnotherActionPageComponent } from './another-action-page/another-action-page.component';

const routes: Routes = [
    {path: 'demo', component: AngularDemoComponent},
    {path: 'about', component: AboutComponent},
    {path: 'action', component: ActionPageComponent},
    {path: 'another-action', component: AnotherActionPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
