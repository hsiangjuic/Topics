<template>
  <div class="member-list-container">
    <h3>會員規定列表</h3>
    <div class="table-responsive">
      <table class="table table-bordered table-hover table-striped member-list">
        <thead>
          <tr>
            <th scope="col" style="background-color: burlywood;">會員等級:</th>
            <th scope="col" style="background-color: burlywood;">會員稱號:</th>
            <th scope="col" style="background-color: burlywood;">所需積分:</th>
            <th scope="col" style="background-color: burlywood;">所需天數：</th>
            <th scope="col" style="background-color: burlywood;">積分增加:</th>
            <th scope="col" style="background-color: burlywood;">會員卡：</th>
            <th scope="col" style="background-color: burlywood;">修改時間：</th>
            <th>
              <button @click="showFormDialog" class="btn btn-primary">新增規定</button>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="member in members" :key="member.mrId">
            <td>{{ member.mrId }}</td>
            <td>{{ member.name }}</td>
            <td>{{ member.neededPoints }}</td>
            <td>{{ member.neededBookingDays }}</td>
            <td>{{ member.gainPoints }}</td>
            <td>
              <img :src="`http://localhost:8080/member/pages/${member.mrId}`" alt="會員卡" class="photo-img"
                style="max-width: 100px; max-height: 100px;" />
            </td>
            <td>{{ member.lastModifiedDate }}</td>
            <td>
              <button @click="editMember(member)" class="btn btn-secondary"><i class="fa fa-pencil"></i></button>&nbsp;
              <button @click="deleteMember(member.mrId)" class="btn btn-danger">
                <i class="fas fa-times"></i>
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>


<script>
import axios from '@/plugins/axios.js';
import Swal from "sweetalert2";
import '@fortawesome/fontawesome-free/css/all.css';

export default {
  data() {
    return {

      members: [],
    };
  },
  methods: {
    async showFormDialog() {
      console.log("999");
      const { value: formValues } = await Swal.fire({
        title: '新增會員等級',
        html: `
          <form id="addMemberForm">
            <label for="mrId">會員等級：</label>
            <input type="number" id="mrId" class="swal2-input" required>
            <label for="name">會員稱號：</label>
            <input type="text" id="name" class="swal2-input" required>
            <label for="neededPoints">所需積分：</label>
            <input type="number" id="neededPoints" class="swal2-input" required>
            <label for="neededBookingDays">所需天數：</label>
            <input type="number" id="neededBookingDays" class="swal2-input" required>
            <label for="gainPoints">積分增加：</label>
            <input type="number" id="gainPoints" class="swal2-input" required>
            <label for="photoFile">會員卡：</label>
            <input type="file" id="photoFile" class="swal2-input" />
          </form>`,
        focusConfirm: false,
        showCancelButton: true,
        confirmButtonText: '提交',
        preConfirm: async () => {
          const mrId = document.getElementById('mrId').value;
          const name = document.getElementById('name').value;
          const neededPoints = document.getElementById('neededPoints').value;
          const neededBookingDays = document.getElementById('neededBookingDays').value;
          const gainPoints = document.getElementById('gainPoints').value;
          const photoFileInput = document.getElementById('photoFile');
          const photoFile = photoFileInput.files[0];
          if (!mrId || !name || !photoFile || !neededPoints || !neededBookingDays || !gainPoints) {
            Swal.showValidationMessage('所有欄位都必須填寫');
            return null;
          }

          try {
            const response = await axios.get(`/member/pages/member/exists/${mrId}`);
            if (response.data.exists) {
              Swal.showValidationMessage('會員等級ID已經存在');
              return null;
            }
          } catch (error) {
            Swal.showValidationMessage('檢查會員等級ID時出錯');
            return null;
          }
          return { mrId, name, photoFile, neededPoints, neededBookingDays, gainPoints };
        }
      });

      if (formValues) {
        this.mrId = formValues.mrId;
        this.name = formValues.name;
        this.photoFile = formValues.photoFile;
        this.neededPoints = formValues.neededPoints;
        this.neededBookingDays = formValues.neededBookingDays;
        this.gainPoints = formValues.gainPoints;
        this.uploadPhoto();
      }
    },
    async uploadPhoto() {
      if (!this.mrId || !this.photoFile) {
        this.message = '提供id和檔案';
        return;
      }

      const formData = new FormData();
      formData.append('mrId', this.mrId);
      formData.append('name', this.name);
      formData.append('photoFile', this.photoFile);
      formData.append('neededPoints', this.neededPoints);
      formData.append('neededBookingDays', this.neededBookingDays);
      formData.append('gainPoints', this.gainPoints);

      try {
        const response = await axios.post('/member/pages/member', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        this.message = '上傳成功!';

        Swal.fire({
          icon: 'success',
          title: '上傳成功!',
        }).then(() => { this.fetchMembers(); });
      } catch (error) {
        console.error('上傳出現問題:', error);
        this.message = '上傳出現問題';

        Swal.fire({
          icon: 'error',
          title: 'Error',
          text: '錯誤',
        });
      }
    },
    async fetchMembers() {
      try {
        const requestData = {};
        const response = await axios.post('/member/pages/member/find', requestData);
        console.log(response.data);
        this.members = response.data.list;
      } catch (error) {
        console.error('獲取會員列表失敗：', error);
        alert('網路異常：' + error.message);
      }
    },
    async deleteMember(mrId) {
      try {
        const response = await axios.delete(`/member/pages/member/${mrId}`);
        if (response.data.success) {
          Swal.fire({
            text: response.data.message,
            icon: 'success',
            allowOutsideClick: false,
            confirmButtonText: '確認'
          }).then(() => { this.fetchMembers(); });
        } else {
          Swal.fire({
            text: response.data.message,
            icon: 'error',
            allowOutsideClick: false,
            confirmButtonText: '確認'
          });
        }
      } catch (error) {
        Swal.fire({
          text: '刪除會員失敗',
          icon: 'error',
          allowOutsideClick: false,
          confirmButtonText: '確認'
        });
        console.error('刪除會員失敗：', error);
      }
    },
    async editMember(member) {
      console.log("999");
      const { value: formValues } = await Swal.fire({
        title: '編輯會員等級',
        html: `
          <form id="editMemberForm">
            <label for="mrId">會員等級：</label>
          <input type="number" id="mrId" class="swal2-input" disabled value="${member.mrId}">
            <label for="editName">會員稱號：</label>
            <input type="text" id="editName" class="swal2-input" value="${member.name}" required>
            <label for="editNeededPoints">所需積分：</label>
            <input type="number" id="editNeededPoints" class="swal2-input" value="${member.neededPoints}" required>
            <label for="editNeededBookingDays">所需天數：</label>
            <input type="number" id="editNeededBookingDays" class="swal2-input" value="${member.neededBookingDays}" required>
            <label for="editGainPoints">積分增加：</label>
            <input type="number" id="editGainPoints" class="swal2-input"  value="${member.gainPoints}" required>
            
            <label for="editPhotoFile">會員卡照片：</label>
            <input type="file" id="editPhotoFile"  class="swal2-input" />

          </form>`,
        focusConfirm: false,
        showCancelButton: true,
        confirmButtonText: '提交',
        preConfirm: async () => {
          const name = document.getElementById('editName').value;
          const neededPoints = document.getElementById('editNeededPoints').value;
          const neededBookingDays = document.getElementById('editNeededBookingDays').value;
          const gainPoints = document.getElementById('editGainPoints').value;
          const photoFileInput = document.getElementById('editPhotoFile');
          const photoFile = photoFileInput.files[0];
          if (!name || !neededPoints || !neededBookingDays || !gainPoints) {
            Swal.showValidationMessage('所有欄位都必須填寫');
            return null;
          }
          return { name, neededPoints, neededBookingDays, gainPoints, photoFile };
        }
      });

      if (formValues) {
        const formData = new FormData();
        formData.append('mrId', member.mrId);
        formData.append('name', formValues.name);
        formData.append('neededPoints', formValues.neededPoints);
        formData.append('neededBookingDays', formValues.neededBookingDays);
        formData.append('gainPoints', formValues.gainPoints);
        if (formValues.photoFile) {
          formData.append('photoFile', formValues.photoFile);
        }

        try {
          const response = await axios.put(`/member/pages/member/${member.mrId}`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
          Swal.fire({
            icon: 'success',
            title: '更新成功!',
          }).then(() => {
            this.fetchMembers();
            location.reload();
          });
        } catch (error) {
          console.error('更新出現問題:', error);
          Swal.fire({
            icon: 'error',
            title: '錯誤',
            text: '更新出現問題',
          });
        }
      }
    }
  },
  mounted() {
    this.fetchMembers();
  },
};
</script>
<style scoped>
.member-list-container {
  min-width: 900px;
  max-width: 1000px;
  margin: 0 auto;
}
</style>