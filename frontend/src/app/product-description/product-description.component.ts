import { Component, OnInit } from '@angular/core';
import { ProductDescription } from './product-description';
import { Observable } from 'rxjs/Rx'
import { ApiService } from '../app-core/api.service';
import { MatSnackBar } from '@angular/material';

@Component({
  selector: 'app-product-description',
  templateUrl: './product-description.component.html',
  styleUrls: ['./product-description.component.css']
})
export class ProductDescriptionComponent implements OnInit {

  private productDescriptions;

  constructor(
    private apiService : ApiService, 
    private snackBar : MatSnackBar
  ) { }

  ngOnInit() {
  }

  public addProductDescription(){
      let newProductDescription : ProductDescription = {
        refNo: "3009002",
        description : {
          matNum: "461952398959",
          names: [
            "NOWA PROWAD.POJ.NA JARZ.ESSENT"
          ]
        }
      }
      this.apiService.post('product-descriptions', newProductDescription).subscribe( addedDescription => {
        let msg = "";
        if(addedDescription !== undefined) {
          msg =  "Added product description: " + JSON.stringify(addedDescription.description);
        } else  {
          msg =  "Error";
        }
        this.snackBar.open(msg);
        console.log(addedDescription);
      });
  }

}
 