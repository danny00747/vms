import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import {RouterModule} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {ToastModule} from 'primeng/toast';
import {MultiSelectModule} from 'primeng/multiselect';
import {BreadcrumbModule} from 'primeng/breadcrumb';


@NgModule({
  declarations: [SidebarComponent, NavbarComponent],
  imports: [
    CommonModule,
    RouterModule,
    ToastModule,
    MultiSelectModule,
    HttpClientModule
  ],
  exports: [
    CommonModule,
    SidebarComponent,
    NavbarComponent,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    MultiSelectModule,
    ToastModule,
    BreadcrumbModule,
  ]
})
export class SharedModule { }
