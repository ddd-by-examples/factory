import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import {MatButtonModule, MatCheckboxModule, MatVerticalStepper, MatStep, MatStepperModule, MatListModule, MatCardModule, MatToolbarModule, MatSnackBarModule} from '@angular/material';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import { ProductDescriptionComponent } from './product-description/product-description.component';
import { ApiService } from './app-core/api.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    ProductDescriptionComponent,
    
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MatButtonModule, 
    MatCheckboxModule,
    MatStepperModule,
    MatListModule,
    MatCardModule,
    MatToolbarModule,
    BrowserAnimationsModule, 
    HttpClientModule, 
    MatSnackBarModule
  ],
  providers: [ApiService],
  bootstrap: [AppComponent]
})
export class AppModule { }
