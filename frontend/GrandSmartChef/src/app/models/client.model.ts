import { TagDTO } from "./tag.model";

export interface ClientDTO{

  id:number;
  email:string;
  fullName?: string;
  birthdate?: string;
  country?: string;
  photoProfile?: string;
  preferences?: TagDTO[];

}
