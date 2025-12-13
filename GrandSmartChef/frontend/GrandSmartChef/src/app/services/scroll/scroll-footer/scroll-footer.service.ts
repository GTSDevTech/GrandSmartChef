import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ScrollFooterService {
  private _footerHidden = signal(false);
  private lastScrollTop = 0;

  footerHidden = this._footerHidden.asReadonly();

  updateScroll(scrollTop:number) {
    const hidden = scrollTop > this.lastScrollTop;
    this._footerHidden.set(hidden);
    this.lastScrollTop = scrollTop;
  }
}
