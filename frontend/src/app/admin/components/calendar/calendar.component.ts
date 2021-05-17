import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, NavigationStart, Router} from '@angular/router';
import {filter} from 'rxjs/operators';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import {DialogService} from 'primeng/dynamicdialog';
import {AddRentComponent} from '@app/admin/components/add-rent/add-rent.component';
import {UserService} from '@app/core/services/user.service';
import {UserInfoDTO} from '@app/shared/models';
import {MenuItem, SelectItem} from 'primeng/api';
import {EToastSeverities, ToastService} from '@app/core/services';
import {Table} from 'primeng/table';
import {CarService} from '@app/core/services/car.service';
import {ViewBookingComponent} from '@app/admin/components/view-booking/view-booking.component';
import {ConfirmationDialogComponent} from '@app/shared/components/confirmation-dialog/confirmation-dialog.component';
import {ConfirmationService} from 'primeng/api';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import autoTable from 'jspdf-autotable';


@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit {

  events: any[] = [];

  options: any;

  bookingDetails: UserInfoDTO;

  users: UserInfoDTO[] = [];
  statuses: SelectItem[];
  clonedUsers: { [s: string]: UserInfoDTO; } = {};
  exportColumns: any[];
  cols: any[];
  bookingStateOption: SelectItem[];
  fullBookoingAction: MenuItem[];
  smallBookoingAction: MenuItem[];
  selectedUsers: UserInfoDTO[] = [];

  constructor(private router: Router,
              private route: ActivatedRoute,
              public toastService: ToastService,
              public carService: CarService,
              private confirmationService: ConfirmationService,
              private userService: UserService,
              private readonly dialogService: DialogService) {
    this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(() => this.reload());
  }

  ngOnInit(): void {
    this.getUsers();
    this.options = {
      plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
      defaultDate: new Date().toISOString(),
      header: {
        left: 'prev,next',
        center: 'title',
        right: 'dayGridMonth,timeGridWeek,timeGridDay'
      },
      dateClick: (e) => {
        // e.dayEl.style.backgroundColor  = 'red';
        // console.log(e.dayEl.style);
      }
    };
    this.statuses = [
      {label: 'Verified', value: 'yes'},
      {label: 'Unverified', value: 'no'}];

    this.bookingStateOption = [
      {label: 'open', value: 'OPEN'},
      {label: 'cancelled', value: 'CANCELLED'},
      {label: 'finished', value: 'FINISHED'},
      {label: 'deleted', value: 'DELETED'}
    ];
    this.fullBookoingAction = [
      {
        id: '1', label: 'View', icon: 'pi pi-eye', command: (event) => {
          console.log(this.bookingDetails);
          this.openViewBookingDialog(this.bookingDetails);
        }
      },
      {
        label: 'Cancel', icon: 'pi pi-minus-circle', command: () => {
          this.cancelBooking();
        }
      },
      {
        label: 'Delete', icon: 'pi pi-times', command: () => {
          this.deleteBooking();
        }
      },
      {separator: true},
      {label: 'Setup', icon: 'pi pi-cog', routerLink: ['/setup']}
    ];
    this.smallBookoingAction = [
      {
        id: '1', label: 'View', icon: 'pi pi-eye', command: (event) => {
          console.log(this.bookingDetails);
          this.openViewBookingDialog(this.bookingDetails);
        }
      },
      {
        label: 'Delete', icon: 'pi pi-times', command: () => {
          this.deleteBooking();
        }
      },
      {separator: true},
      {label: 'Setup', icon: 'pi pi-cog'}
    ];

    this.cols = [
      {field: 'username', header: 'Username'},
      {field: 'userEmail', header: 'Email'},
      {field: 'phoneNumber', header: 'Phone'}
    ];
    this.exportColumns = this.cols.map(col => ({title: col.header, dataKey: col.field}));
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  openAddRentDialog(): void {
    const ref = this.dialogService.open(AddRentComponent, {
      header: 'Add A Rent',
    });

    ref.onClose.subscribe((formId: number) => {
      if (formId) {
      }
    });
  }

  confirm(event: Event): void {
    this.confirmationService.confirm({
      target: event.target,
      message: 'Are you sure that you want to proceed?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.toastService.show(EToastSeverities.INFO, 'confirmed');
      },
      reject: () => {
        this.toastService.show(EToastSeverities.ERROR, 'rejected');
      }
    });
  }

  openViewBookingDialog(bookingDetails: UserInfoDTO): void {
    const ref = this.dialogService.open(ViewBookingComponent, {
      header: 'Booking Details',
      data: bookingDetails
    });

    ref.onClose.subscribe((formId: number) => {
      if (formId) {
      }
    });
  }

  getUsers(): void {
    this.userService.getAllUsers()
      .subscribe(
        (data: UserInfoDTO[]) => {
          this.users = data;
          console.log(this.users.length);
          this.users
            .filter(x => x.bookingDTO)
            .forEach((u, i) => {
              const car = data.find(x => x.username === u.username);
              this.events.push({
                id: i,
                title: (u.bookingDTO.carDTO) ? `${car.bookingDTO.carDTO.modelDTO.brand} ${car.bookingDTO.carDTO.modelDTO.modelType} booked by ${u.username}`
                  : (u.bookingDTO.cancellationDate) ? `Cancelled reservation by ${u.username}`
                    : (u.bookingDTO.bookingState === 'FINISHED') ? `Finished reservation by ${u.username}` : '',
                start: u.bookingDTO.withdrawalDate,
                end: u.bookingDTO.returnDate,
                color: (u.bookingDTO.cancellationDate) ? '#DC143C' : (u.bookingDTO.bookingState === 'FINISHED') ? '#e49b0f' : ''
              });
            });
        },
        error => {
          console.error(error);
        });
  }

  cancelBooking(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'You are about to cancel this reservation, do you want to continue ? '
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
      }
    });
  }

  deleteBooking(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'You are about to delete this reservation, do you want to continue ? '
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
      }
    });
  }

  deleteUser(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'You are about to delete this user, do you want to continue ? '
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
      }
    });
  }

  deleteSelectedUsers(): void {
    const t = this.users.filter(val => this.selectedUsers.includes(val));
    console.log(t);
    this.selectedUsers = null;
  }

  getClikedDate(fc: any): void {

    let clickedUsername = '';
    if ((fc as HTMLElement).tagName === 'SPAN') {
      const target = fc as HTMLElement;
      clickedUsername = target?.innerHTML.split('by')[1]?.substring(1);
    } else if ((fc as HTMLElement).tagName === 'DIV') {
      const target = fc as HTMLElement;
      const target1 = target.innerHTML as unknown as HTMLElement;
      clickedUsername = JSON.stringify(target1)?.split('by')[1]?.split('<')[0]?.substring(1);
    }

    if (clickedUsername) {
      const user = this.users
        .find(u => u.username === clickedUsername);
      if (user){
        this.openViewBookingDialog(user);
      }
    }
  }

  onRowEditInit(user: UserInfoDTO): void {
    this.clonedUsers[user.userId] = {...user};
  }

  onRowEditSave(user: UserInfoDTO): void {
    delete this.clonedUsers[user.userId];
    this.toastService.show(EToastSeverities.INFO, 'user info updated');
    const t = this.users.find(x => x.userId === user.userId);
    console.log(t);
  }

  onRowEditCancel(user: UserInfoDTO, index: number): void {
    this.users[index] = this.clonedUsers[user.userId];
    delete this.clonedUsers[user.userId];
  }

  exportPdf(): void {
    /*
        import('jspdf').then(jsPDF => {
      import('jspdf-autotable').then(x => {
        const doc = new jsPDF.default(0, 0);
        doc.text(35, 15, 'VmsApp Users');
        doc.autoTable(this.exportColumns, (this.selectedUsers.length === 0) ? this.users : this.selectedUsers);
        doc.save('users.pdf');
      });
    });
     */

    const doc = new jsPDF();

    doc.text('VmsApp Users', 14, 16);
    doc.autoTable({
      columns: [
        { dataKey: 'username', header: 'Username' },
        { dataKey: 'userEmail', header: 'Email' },
        { dataKey: 'phoneNumber', header: 'Phone' },
      ], body: (this.selectedUsers.length === 0) ? this.users : this.selectedUsers, theme: 'grid', startY: 20});

    doc.save('users.pdf');

  }

  exportExcel(): void {
    const excelUsers = [];
    this.users.forEach(x => excelUsers.push({username: x.username, email: x.userEmail, phone: x.phoneNumber}));
    import('xlsx').then(xlsx => {
      const worksheet = xlsx.utils.json_to_sheet(excelUsers);
      const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
      const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
      this.saveAsExcelFile(excelBuffer, 'users');
    });
  }

  saveAsExcelFile(buffer: any, fileName: string): void {
    import('file-saver').then(FileSaver => {
      const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
      const EXCEL_EXTENSION = '.xlsx';
      const data: Blob = new Blob([buffer], {
        type: EXCEL_TYPE
      });
      FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    });
  }

  clear(table: Table): void {
    table.clear();
  }

  returnArrary(o: any): any[] {
    let s: any[];
    s = [o];
    return s.flatMap(x => x);
  }

  userBookingToView(user: any): void {
    this.bookingDetails = user;
  }
}
