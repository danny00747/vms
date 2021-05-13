import { Injectable } from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import { RoleType } from '../enum/role-type.enum';
import {AuthentificationService} from '@app/core/services';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {

  constructor(
    private router: Router,
    private authenticationService: AuthentificationService
  ) {
  }

  async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<boolean> {
    const adminRole: string = this.authenticationService.currentTokenRoleValue;
    if (adminRole === RoleType.ADMIN) {
      // logged in so return true
      return true;
    }

    // redirect to home page
    await this.router.navigate(['/home']);
    return false;
  }
}
