import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PostCreateAccountPage } from './post-create-account.page';

describe('PostCreateAccountPage', () => {
  let component: PostCreateAccountPage;
  let fixture: ComponentFixture<PostCreateAccountPage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(PostCreateAccountPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
