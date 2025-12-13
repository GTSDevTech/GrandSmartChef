import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AddRecipeToCollectionSheetComponent } from './add-recipe-to-collection-sheet.component';

describe('AddRecipeToCollectionSheetComponent', () => {
  let component: AddRecipeToCollectionSheetComponent;
  let fixture: ComponentFixture<AddRecipeToCollectionSheetComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddRecipeToCollectionSheetComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AddRecipeToCollectionSheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
