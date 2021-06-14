import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FilterService, SelectItemGroup} from 'primeng/api';
import {AuthentificationService} from '@app/core/services';
import {RoleType} from '@app/core/enum/role-type.enum';
import {UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';
import {ConfirmationDialogComponent} from '@app/shared/components/confirmation-dialog/confirmation-dialog.component';
import {EToastSeverities, ToastService} from '@app/core/services';
import {DialogService} from 'primeng/dynamicdialog';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  userRole: string;
  username: string;
  user: UserInfoDTO;
  filteredBrands = [];
  groupedBrands: SelectItemGroup[];
  selectedBrands: any[] = [];

  visibleSideBar = false;

  constructor(
    public authService: AuthentificationService,
    public toastService: ToastService,
    private filterService: FilterService,
    private readonly dialogService: DialogService,
    private userService: UserService,
    private router: Router
  ) {
  }


  ngOnInit(): void {
    this.groupedBrands = [
      {
        label: 'Opel', value: 'op',
        items: [
          {label: 'Astra', value: 'Opel'},
          {label: 'Corsa', value: 'Opel'},
          {label: 'Mokka', value: 'Opel'},
          {label: 'Vivaro', value: 'Opel'},
          {label: 'Zafira', value: 'Opel'},
        ]
      },
      {
        label: 'Ford', value: 'fd',
        items: [
          {label: 'C-Max', value: 'Ford'},
          {label: 'Fiesta', value: 'Ford'},
          {label: 'Focus', value: 'Ford'},
          {label: 'Mondeo', value: 'Ford'}
        ]
      },
      {
        label: 'Toyota', value: 'ty',
        items: [
          {label: 'Aventis', value: 'Toyota'},
          {label: 'Auris', value: 'Toyota'},
          {label: 'Aygo', value: 'Toyota'},
          {label: 'Camry', value: 'Toyota'},
          {label: 'Corolla', value: 'Toyota'},
          {label: 'Yaris', value: 'Toyota'},
        ]
      },
      {
        label: 'Volkswagen', value: 'vk',
        items: [
          {label: 'Caddy', value: 'Volkswagen'},
          {label: 'Golf', value: 'Volkswagen'},
          {label: 'T-ROC', value: 'Volkswagen'},
          {label: 'Touran', value: 'Volkswagen'},
          {label: 'Passat', value: 'Volkswagen'},
          {label: 'Polo', value: 'Volkswagen'}
        ]
      },
      {
        label: 'Renault', value: 'rn',
        items: [
          {label: 'Captur', value: 'Renault'},
          {label: 'Espace', value: 'Renault'},
          {label: 'Megane', value: 'Renault'},
          {label: 'Traffic', value: 'Renault'},
          {label: 'Twingo', value: 'Renault'},
        ]
      },
      {
        label: 'Nissan', value: 'ns',
        items: [
          {label: 'Evalia', value: 'Nissan'},
          {label: 'Juke', value: 'Nissan'},
          {label: 'Pulsar', value: 'Nissan'},
          {label: 'Qashqai', value: 'Nissan'},
        ]
      },
      {
        label: 'Fiat', value: 'ft',
        items: [
          {label: '500X', value: 'Fiat'},
          {label: 'Punto', value: 'Fiat'}
        ]
      },
      {
        label: 'Kia', value: 'ki',
        items: [
          {label: 'Venga', value: 'Kia'},
          {label: 'Stonic', value: 'Kia'}
        ]
      },
      {
        label: 'Honda', value: 'hd',
        items: [
          {label: 'Civic', value: 'Honda'},
          {label: 'CR-V', value: 'Honda'}
        ]
      },
      {
        label: 'BMW', value: 'bm',
        items: [
          {label: '318', value: 'BMW'},
          {label: 'X1', value: 'BMW'}
        ]
      },
      {
        label: 'Volvo', value: 'vl',
        items: [
          {label: 'V40', value: 'Volvo'}
        ]
      },
      {
        label: 'Suzuki', value: 'sk',
        items: [
          {label: 'Baleno', value: 'Suzuki'}
        ]
      },
      {
        label: 'Peugeot', value: 'pt',
        items: [
          {label: '208', value: 'Peugeot'},
          {label: '5008', value: 'Peugeot'}
        ]
      },
      {
        label: 'Chevrolet', value: 'ch',
        items: [
          {label: 'Spark', value: 'Chevrolet'}
        ]
      }
    ];
    this.getUserRole();
    this.getUsername();
  }

  reload(): void {
    setTimeout(() => window.location.reload(), 100);
  }

  async onLogOutClick(): Promise<void> {
    this.authService.logout();
    await this.router.navigate(['/login']);
    this.reload();
  }

  getUserRole(): void {
    const adminRole: string = this.authService.currentTokenRoleValue;
    if (adminRole === RoleType.ADMIN) {
      this.userRole = 'Admin';
    } else {
      this.userRole = 'Client';
    }
  }

  getUsername(): void {
    this.userService.getUserByJwt()
      .subscribe(
        (data: UserInfoDTO) => {
          this.username = data.username;
          this.user = data;
        },
        error => {
          console.error(error);
        });
  }

  confirmCancel(): void {
    const ref = this.dialogService.open(ConfirmationDialogComponent, {
      header: 'Confirmation',
      data: {
        message: 'Are you sure that you want to cancel this reservation?'
      }
    });
    ref.onClose.subscribe((confirm: boolean) => {
      if (confirm) {
        this.toastService.show(EToastSeverities.SUCCESS, 'cancelled !');
      }
    });
  }

  filterBrand(event: any): void {

    const query = event.query.charAt(0).toUpperCase() + event.query.slice(1);
    const filteredBrands = [];

    this.groupedBrands.forEach(optgroup => {
      const filteredSubOptions = this.filterService.filter(optgroup.items, ['value'], query, 'contains');
      if (filteredSubOptions && filteredSubOptions.length) {
        filteredBrands.push({
          label: optgroup.label,
          value: optgroup.value,
          items: filteredSubOptions
        });
      }
    });
    this.filteredBrands = filteredBrands;
  }

  getBrands(brands: any): void {
    this.selectedBrands = brands.value;
  }

  async submitQuery(): Promise<void> {
    await this.router.navigate(['/cars'],
      {
        queryParams:
          {
            pickUpDate: new Date().toISOString(),
            brands: [...new Set(this.selectedBrands.map(x => x.value))]
          }
      });
  }

  showSideBar(): void {
    this.visibleSideBar = true;
  }

  closeSide(event: boolean): void {
    this.visibleSideBar = event;
  }
}
