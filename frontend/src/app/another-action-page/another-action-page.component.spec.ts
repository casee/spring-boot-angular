import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AnotherActionPageComponent } from './another-action-page.component';

describe('AnotherActionPageComponent', () => {
  let component: AnotherActionPageComponent;
  let fixture: ComponentFixture<AnotherActionPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AnotherActionPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AnotherActionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
