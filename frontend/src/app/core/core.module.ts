import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './components/home/home.component';
import { FooterComponent } from './components/footer/footer.component';
import { ContactComponent } from './components/contact/contact.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import {Routes, RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import { AboutComponent } from './components/about/about.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {JwtInterceptor} from './interceptor/jwt.interceptor';
import {ErrorInterceptor} from './interceptor/error.interceptor';
import {MessageService} from 'primeng/api';
import { FaqComponent } from './components/faq/faq.component';
import {JwtHelperService} from '@auth0/angular-jwt';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'contact', component: ContactComponent},
  {path: 'about', component: AboutComponent},
  {path: 'faq', component: FaqComponent},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  declarations: [HomeComponent, FooterComponent, ContactComponent,
    AboutComponent, PageNotFoundComponent, FaqComponent],
  exports: [
    FooterComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule.forChild(routes)
  ],
  providers: [
    MessageService,
    JwtHelperService,
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ]
})
export class CoreModule { }
