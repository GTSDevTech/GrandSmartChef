import {Component, inject, input, OnInit} from '@angular/core';
import {IonCol, IonGrid, IonIcon, IonImg, IonItem, IonLabel, IonRow} from "@ionic/angular/standalone";
import {CategoryDTO} from "../../../models/category.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-category-item',
  templateUrl: './category-item.component.html',
  styleUrls: ['./category-item.component.scss'],
  imports: [
    IonImg,
    IonItem,
    IonCol,
    IonLabel,
    IonIcon,
    IonGrid,
    IonRow
  ]
})
export class CategoryItemComponent implements OnInit {

  private router = inject(Router);
  category = input.required<CategoryDTO>();
  constructor() { }

  ngOnInit() {

  }


  onCategoryClick(){
    this.router.navigate(['/ingredients'], {queryParams: {categoryId: this.category().id}})
  }



}
