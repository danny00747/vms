import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './components/login/login.component';
import {RegisterComponent} from './components/register/register.component';
import {Routes, RouterModule} from '@angular/router';
import {SharedModule} from '../shared/shared.module';
import {GalleryComponent} from './components/gallery/gallery.component';
import {ButtonModule} from 'primeng/button';
import {ProfileComponent} from './components/profile/profile.component';
import {AuthGuard} from '@app/core/guards/auth.guard';
import {CarsComponent} from './components/cars/cars.component';
import {CarDetailComponent} from './components/cars/car-detail/car-detail.component';
import {ReservationComponent} from './components/reservation/reservation.component';
import {CarsResolver} from '@app/core/resolvers/cars.resolver';
import {SkeletonModule} from 'primeng/skeleton';
import {PaginatorModule} from 'primeng/paginator';
import {DataViewModule} from 'primeng/dataview';
import {CascadeSelectModule} from 'primeng/cascadeselect';

const routes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'gallery', component: GalleryComponent},
  {path: 'cars', component: CarsComponent},
  {path: 'cars/:id', component: CarDetailComponent},
  {path: 'reservation/:carId', component: ReservationComponent}
];

// {path: 'cars', component: CarsComponent, resolve: {cars: CarsResolver}},

@NgModule({
  declarations: [LoginComponent, RegisterComponent, GalleryComponent,
    ProfileComponent, CarsComponent, CarDetailComponent, ReservationComponent],
  imports: [
    CommonModule,
    SharedModule,
    ButtonModule,
    SkeletonModule,
    PaginatorModule,
    DataViewModule,
    CascadeSelectModule,
    RouterModule.forChild(routes)
  ]
})
export class FeaturesModule {
}
