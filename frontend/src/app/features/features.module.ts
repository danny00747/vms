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
import {SkeletonModule} from 'primeng/skeleton';
import {PaginatorModule} from 'primeng/paginator';
import {DataViewModule} from 'primeng/dataview';
import {CascadeSelectModule} from 'primeng/cascadeselect';
import {VerifyEmailComponent} from './components/register/verify-email/verify-email.component';
import {EditProfileComponent} from './components/profile/edit-profile/edit-profile.component';
import {DialogService} from 'primeng/dynamicdialog';
import { ReservationRecapComponent } from './components/reservation/reservation-recap/reservation-recap.component';
import {ConfirmationService} from 'primeng/api';

const routes: Routes = [
  {path: 'register', component: RegisterComponent},
  {path: 'login', component: LoginComponent},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'gallery', component: GalleryComponent},
  {path: 'cars', component: CarsComponent},
  {path: 'cars/:id', component: CarDetailComponent},
  {path: 'reservation/:carId', component: ReservationComponent},
  {path: 'reservation/recap/:bookingId', component: ReservationRecapComponent},
  {path: 'verify-email/:key', component: VerifyEmailComponent},
];

// {path: 'cars', component: CarsComponent, resolve: {cars: CarsResolver}},

@NgModule({
  declarations: [LoginComponent, RegisterComponent, GalleryComponent,
    ProfileComponent, CarsComponent, CarDetailComponent, ReservationComponent, VerifyEmailComponent, EditProfileComponent, ReservationRecapComponent],
  imports: [
    CommonModule,
    SharedModule,
    ButtonModule,
    SkeletonModule,
    PaginatorModule,
    DataViewModule,
    CascadeSelectModule,
    RouterModule.forChild(routes)
  ], providers: [DialogService, ConfirmationService]
})
export class FeaturesModule {
}
