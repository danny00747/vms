import { Component, OnInit } from '@angular/core';
import {DynamicDialogConfig, DynamicDialogRef} from 'primeng/dynamicdialog';
import {Observable} from 'rxjs';
import {PrincingDetailsDTO, UserInfoDTO} from '@app/shared/models';
import {UserService} from '@app/core/services/user.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {

  username: string;
  userEmail: string;
  password: string;
  phoneNumber: number;

  initialUsername: string;

  constructor(
    public ref: DynamicDialogRef,
    private config: DynamicDialogConfig,
    private userService: UserService) { }

  ngOnInit(): void {
    this.initialUsername = this.config.data.user.username;
    this.username = this.config.data.user.username;
    this.userEmail = this.config.data.user.userEmail;
    this.phoneNumber = this.config.data.user.phoneNumber;
  }

  submit(): void {
    const updatedUser = {
      username : this.username,
      userEmail : this.userEmail,
      password : '1234',
      phoneNumber : this.phoneNumber,
    };

    const sub$: Observable<UserInfoDTO> =
      this.userService.patchUser(this.initialUsername, updatedUser);

    sub$.subscribe((e: UserInfoDTO) => {
      this.ref.close(e);
    });
  }
}
