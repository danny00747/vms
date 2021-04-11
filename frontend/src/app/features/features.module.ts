import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import {Routes, RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import { GalleryComponent } from './components/gallery/gallery.component';
import {ButtonModule} from 'primeng/button';
import { ProfileComponent } from './components/profile/profile.component';
import {AuthGuard} from '@app/core/guards/auth.guard';
import { CarsComponent } from './components/cars/cars.component';
import { CarDetailComponent } from './components/cars/car-detail/car-detail.component';
import { ReservationComponent } from './components/reservation/reservation.component';

const routes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'gallery', component: GalleryComponent},
  {path: 'cars', component: CarsComponent},
  {path: 'cars/:name', component: CarDetailComponent},
  {path: 'reservation/:name', component: ReservationComponent}
];

@NgModule({
  declarations: [LoginComponent, RegisterComponent, GalleryComponent,
    ProfileComponent, CarsComponent, CarDetailComponent, ReservationComponent],
  imports: [
    CommonModule,
    SharedModule,
    ButtonModule,
    RouterModule.forChild(routes)
  ]
})
export class FeaturesModule { }
