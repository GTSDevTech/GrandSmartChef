import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { AddRecipeToCollectionModalComponent } from './add-recipe-to-collection-modal.component';

describe('AddRecipeToCollectionSheetComponent', () => {
  let component: AddRecipeToCollectionModalComponent;
  let fixture: ComponentFixture<AddRecipeToCollectionModalComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddRecipeToCollectionModalComponent ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(AddRecipeToCollectionModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
