import {Component, computed, inject, OnInit, signal} from '@angular/core';
import {
  IonContent,
  IonHeader,
  IonItem,
  IonLabel,
  IonList,
  IonModal,
  IonSearchbar,
  IonToolbar
} from "@ionic/angular/standalone";
import {ModalService} from "../../../../services/modal/modal.service";
import {TagDTO} from "../../../../models/tag.model";

@Component({
  selector: 'app-tag-modal',
  templateUrl: './tag-modal.component.html',
  styleUrls: ['./tag-modal.component.scss'],
  imports: [
    IonModal,
    IonContent,
    IonSearchbar,
    IonList,
    IonItem,
    IonLabel,
    IonHeader,
    IonToolbar
  ]
})
export class TagModalComponent  implements OnInit {

  private modalService= inject(ModalService);
  isOpen = this.modalService.isOpen("tag-modal");
  tags = signal<TagDTO[]>([]);
  disabledTagIds = signal<Set<number>>(new Set())
  searchText = signal("");


  ngOnInit() {}


  filteredTags = computed(() =>{
    const value = this.searchText().toLowerCase();
    return this.tags().filter(t =>
      t.name.toLowerCase().includes(value)
    );
  });

  open(data: { tags: TagDTO[]; disabledIds: number[] }) {
    this.tags.set(data.tags);
    this.disabledTagIds.set(new Set(data.disabledIds));
    this.searchText.set('');
    this.modalService.open('tag-modal');
  }

  isDisabled(tag: TagDTO): boolean {
    return this.disabledTagIds().has(tag.id);
  }

  select(tag: TagDTO){
    this.modalService.close('tag-modal', tag)
  }

  close(){
    this.modalService.close('tag-modal')
  }

  onSearch(event: Event){
    const value = (event.target as HTMLIonSearchbarElement).value || '';
    this.searchText.set(value);
  }

}
