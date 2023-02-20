<template>
   <div class="px-1">
      <v-spacer></v-spacer>
      <h2 class="text--secondary mb-2">Parameter management</h2>
      <v-spacer></v-spacer>

       <v-data-table
           :headers="headers"
           :items="items"
           show-expand
           single-expand
           item-key="id"
           :search="search"
           @item-expanded="loadDetails"
           class="elevation-1"
           sort-by="id"
       >
       <template v-slot:top>
      <v-toolbar
        flat
      >
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="dialog" max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              class="mb-2"
              v-bind="attrs"
              v-on="on"
            >
               Naujas
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

            <v-card-text>
              <v-container>
                <v-row>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.kodas"
                      label="Kodas"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.pavadinimas"
                      label="Pavadinimas"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.aprasymas"
                      label="Aprasymas"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.galiojaNuo"
                      label="Galioja Nuo"
                    ></v-text-field>
                  </v-col>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                  >
                    <v-text-field
                      v-model="editedItem.galiojaIki"
                      label="Galioja Iki"
                    ></v-text-field>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue darken-1"
                text
                @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="saveParent"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="500px">
          <v-card>
            <v-card-title class="text-h5">Ar tikrai norite ištrinti?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Atšaukti</v-btn>
              <v-btn color="blue darken-1" text @click="deleteParameterConfirm">Ok</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="editParentItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="deleteParentItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
         <template v-slot:expanded-item="{ headers }">
               <td :colspan="headers.length">
                  <v-data-table
                     :headers="valueHeaders"
                     :items="itemDetals"
                     item-key="parametroReikmesId"
                     :search="search"
                  >
                  <template v-slot:item.actions="{ item }">
                     <v-icon
                     small
                     class="mr-2"
                     @click="editItem(item)"
                     >
                     mdi-pencil
                     </v-icon>
                     <v-icon
                     small
                     @click="deleteItem(item)"
                     >
                     mdi-delete
                     </v-icon>
                  </template>
                  </v-data-table>
               </td>
         </template>
       </v-data-table>
   </div>
</template>
<script>
import axiosinstance from '../plugins/axiosInstance';
export default {
   data() {
       return {
           search: '',
           items: [],
           itemDetals: [],
           dialog: false,
           dialogDelete: false,
           valueHeaders: [
               {
                   text: 'Id',
                   align: 'left',
                   filterable: false,
                   value: 'parametroReikmesId'
               },
               { text: 'Reiksme', value: 'reiksme' },
               { text: 'Galioja nuo', value: 'galiojaNuo' },
               { text: 'Galioja iki', value: 'galiojaIki' },
               { text: 'Veiksmai', value: 'actions', sortable: false }
           ],
           headers: [
               {
                   text: 'Id',
                   align: 'left',
                   filterable: false,
                   value: 'id'
               },
               { text: 'Kodas', value: 'kodas' },
               { text: 'Pavadinimas ', value: 'pavadinimas' },
               { text: 'Aprasymas', value: 'aprasymas' },
               { text: 'Galioja nuo', value: 'galiojaNuo' },
               { text: 'Galioja iki', value: 'galiojaIki' },
               { text: 'Veiksmai', value: 'actions', sortable: false }
           ],
           editedIndex: -1,
            defaultItem: {
            },
            editedItem: {
            }
       }
   },
   async mounted() {
      const response = await axiosinstance.get("parameters");
      this.items = response.data.items;
   },
   computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'Sukurti nauja' : 'Redaguoti'
      },
    },
    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },
   methods: {
      async loadDetails({ item }) {
         const response = await axiosinstance.get(`parameters/${item.id}/values`);
         item.details = response.data
         this.itemDetals = response.data;
         console.log(response.data);
      },
      editParentItem (item) {
         this.editedIndex = this.items.indexOf(item);
         this.editedItem = Object.assign({}, item);
         this.dialog = true;
      },
      deleteParentItem (item) {
         this.editedIndex = this.items.indexOf(item);
         this.editedItem = Object.assign({}, item);
         this.dialogDelete = true;
      },
      editItem (item) {
         this.editedIndex = this.items.indexOf(item);
         this.editedItem = Object.assign({}, item);
         this.dialog = true;
      },
      async deleteParameterConfirm () {
         const deleted = this.items.splice(this.editedIndex, 1);
         console.log(deleted);
         await axiosinstance.delete(`/parameters/${deleted[0].id}`);
         this.closeDelete();
      },
      close () {
         this.dialog = false
         this.$nextTick(() => {
            this.editedItem = Object.assign({}, this.defaultItem)
            this.editedIndex = -1
         })
      },
      closeDelete () {
         this.dialogDelete = false
         this.$nextTick(() => {
            this.editedItem = Object.assign({}, this.defaultItem)
            this.editedIndex = -1
         })
      },
      async saveParent () {
         if (this.editedIndex > -1) {
            const newValue = Object.assign(this.items[this.editedIndex], this.editedItem);
            await axiosinstance.put(`/parameters/${newValue.id}`, newValue);
         } else {
            const newItem = await axiosinstance.post(`/parameters`, this.editedItem);
            this.items.push(newItem.data);
         }
         this.close()
      }
   }
}
</script>