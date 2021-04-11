import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FullCalendarModule} from 'primeng/fullcalendar';
import {RouterModule, Routes} from '@angular/router';
import {SharedModule} from '@app/shared/shared.module';
import {CalendarComponent} from './components/calendar/calendar.component';


const appRoutes: Routes = [
  { path: 'admin/calendar', component: CalendarComponent},
];

@NgModule({
  declarations: [CalendarComponent],
  imports: [
    CommonModule,
    FullCalendarModule,
    SharedModule,
    RouterModule.forChild(appRoutes),
  ]
})
export class AdminModule { }

// import {FullCalendarModule} from 'primeng/fullcalendar';
