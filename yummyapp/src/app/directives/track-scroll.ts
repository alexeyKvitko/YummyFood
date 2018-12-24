import {Directive, EventEmitter, HostListener, Output} from "@angular/core";

@Directive({
  selector: '[track-scroll]',
})

export class TrackScrollDirective {

  @Output() onScroll = new EventEmitter<number>();

  percentValue: number = 0;

  @HostListener("scroll", ["$event"])
  onListenerTriggered(event: UIEvent): void {
    const percent = Math.round((event.srcElement.scrollTop / (event.srcElement.scrollHeight - event.srcElement.clientHeight)) * 100);
    if(this.percentValue !== percent){
      this.percentValue = percent;
      this.onScroll.emit(percent);
    }
  }
}
