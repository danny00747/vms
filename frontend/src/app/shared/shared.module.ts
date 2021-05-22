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
import {SliderModule} from 'primeng/slider';
import {RatingModule} from 'primeng/rating';
import {PasswordModule} from 'primeng/password';
import {CalendarModule} from 'primeng/calendar';
import {TabViewModule} from 'primeng/tabview';
import {TableModule} from 'primeng/table';
import {InputTextModule} from 'primeng/inputtext';
import {RippleModule} from 'primeng/ripple';
import {ToolbarModule} from 'primeng/toolbar';
import {TooltipModule} from 'primeng/tooltip';
import {DropdownModule} from 'primeng/dropdown';
import {InputMaskModule} from 'primeng/inputmask';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { TagModule } from 'primeng/tag';
import {BadgeModule} from 'primeng/badge';
import {SplitButtonModule} from 'primeng/splitbutton';
import {DividerModule} from 'primeng/divider';
import {CardModule} from 'primeng/card';
import {DialogModule} from 'primeng/dialog';
import {ConfirmPopupModule} from 'primeng/confirmpopup';
import { ChipModule } from 'primeng/chip';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {DataViewModule} from 'primeng/dataview';
import {PickListModule} from 'primeng/picklist';
import { TruncatePipe } from './pipes/truncate.pipe';
import {DialogService} from 'primeng/dynamicdialog';
import {InputNumberModule} from 'primeng/inputnumber';

@NgModule({
  declarations: [SidebarComponent, NavbarComponent, ConfirmationDialogComponent, TruncatePipe],
  imports: [
    CommonModule,
    RouterModule,
    ToastModule,
    MultiSelectModule,
    RatingModule,
    HttpClientModule,
    PasswordModule,
    DialogModule,
    DataViewModule,
    PickListModule,
    InputNumberModule,
    MessagesModule,
    MessageModule,
    ChipModule,
    InputTextModule,
    RippleModule,
    InputMaskModule,
    DividerModule,
    ConfirmPopupModule,
    CardModule,
    TabViewModule,
    SplitButtonModule,
    TooltipModule,
    TagModule,
    BadgeModule,
    ToolbarModule,
    CalendarModule,
    DropdownModule,
    TableModule,
    SliderModule
  ],
  exports: [
    CommonModule,
    SidebarComponent,
    NavbarComponent,
    FormsModule,
    ReactiveFormsModule,
    NgbModule,
    HttpClientModule,
    MessagesModule,
    MessageModule,
    ConfirmPopupModule,
    RatingModule,
    PickListModule,
    DataViewModule,
    InputNumberModule,
    ToolbarModule,
    ChipModule,
    DividerModule,
    DialogModule,
    TruncatePipe,
    CardModule,
    MultiSelectModule,
    TooltipModule,
    TagModule,
    SplitButtonModule,
    TabViewModule,
    BadgeModule,
    InputMaskModule,
    ToastModule,
    InputTextModule,
    DropdownModule,
    RippleModule,
    TableModule,
    CalendarModule,
    PasswordModule,
    BreadcrumbModule,
    SliderModule
  ], providers: [DialogService]
})
export class SharedModule { }
