import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FullCalendarModule} from 'primeng/fullcalendar';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '@app/shared/shared.module';
import {CalendarComponent} from './components/calendar/calendar.component';
import {AddRentComponent} from './components/add-rent/add-rent.component';
import {AdminGuard} from '@app/core/guards/admin.guard';
import { ViewBookingComponent } from './components/view-booking/view-booking.component';
import {DialogService} from 'primeng/dynamicdialog';
import {ConfirmationService} from 'primeng/api';
import { EditCarsListComponent } from './components/edit-cars-list/edit-cars-list.component';
import { CarMaintenanceComponent } from './components/car-maintenance/car-maintenance.component';
import { RentListComponent } from './components/rent-list/rent-list.component';
import { EditCarsDialogComponent } from './components/edit-cars-list/edit-cars-dialog/edit-cars-dialog.component';

const appRoutes: Routes = [
  {
    path: 'admin/management',
    component: CalendarComponent,
    canActivate: [AdminGuard]
  },
];

@NgModule({
  declarations: [CalendarComponent, AddRentComponent, ViewBookingComponent, EditCarsListComponent, CarMaintenanceComponent, RentListComponent, EditCarsDialogComponent],
  imports: [
    CommonModule,
    FullCalendarModule,
    SharedModule,
    RouterModule.forChild(appRoutes),
  ], providers: [ConfirmationService]
})
export class AdminModule {
}

// import {FullCalendarModule} from 'primeng/fullcalendar';
