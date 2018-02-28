import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatVerticalStepper, MatStep, MatStepperModule, MatCardModule, MatToolbarModule, MatSnackBarModule} from '@angular/material';
import { AppComponent } from './app.component';
import { ProductDescriptionComponent } from './product-description/product-description.component';
import { ApiService } from './app-core/api.service';


@NgModule({
  declarations: [
    AppComponent,
    ProductDescriptionComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatButtonModule, 
    MatStepperModule,
    MatCardModule,
    MatSnackBarModule,
    MatToolbarModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
