import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs/Rx'

@Injectable()
export class ApiService {

  constructor(
    private httpClient : HttpClient
  ) { }

  public post<T>( resource: string, payload : T) : Observable<T>{
    return this.httpClient.post<T>(environment.endpoint + '/' + resource, payload);
  }

}
