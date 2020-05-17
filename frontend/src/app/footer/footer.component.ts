import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  version: string;

  constructor(private http: HttpClient) {
      this.http.get("frakton/api/version", {responseType: 'text'}).subscribe(data => {
          this.version = data;
      });
  }

  ngOnInit(): void {
  }

}
