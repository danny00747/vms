import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AppComponent} from './app.component';
import {AdminModule} from '@app/admin/admin.module';
import {SharedModule} from './shared/shared.module';
import {CoreModule} from './core/core.module';
import {Routes, RouterModule} from '@angular/router';
import {FeaturesModule} from './features/features.module';
import {JwtModule} from '@auth0/angular-jwt';


const routes: Routes = [
  { path: 'admin', loadChildren: () => import(`./admin/admin.module`)
      .then(module => module.AdminModule) },
  {
    path: 'features', loadChildren: () =>
      import('./features/features.module').then(m => m.FeaturesModule),
  }
];

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AdminModule,
    FeaturesModule,
    SharedModule,
    CoreModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: () => localStorage.getItem('currentToken')
      }
    }),
    RouterModule.forRoot(routes),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
