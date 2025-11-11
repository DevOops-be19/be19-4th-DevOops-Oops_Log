import api from './axios';

// 태그 등록(관리자)
export async function createTag({ tagName, tagType }) {
    const body = { tagName, tagType };
    const { data } = await api.post('/tag/tag-insert', body);
    return data;
}

// 태그 삭제(관리자)
export async function deleteTag({ tagId }) {
    const { data } = await api.delete(`/tag/tag-delete/${tagId}`);
    return data;
}

// ooh에 사용된 태그 삭제(ooh기록 삭제 시 같이 삭제되도록 사용)
export async function deleteUsedOohTag({ tagId, oohId }) {
    const { data } = await api.delete(`/tag/oohtag-delete/${tagId}/${oohId}`);
    return data;
}

// oops에 사용된 태그 삭제(oops기록 삭제 시 같이 삭제되도록 사용)
export async function deleteUsedOopsTag({ tagId, oopsId }) {
    const { data } = await api.delete(`/tag/oopstag-delete/${tagId}/${oopsId}`);
    return data;
}

// 태그 수정(관리자)
export async function modifyTag({ tagId, tagName, tagType }) {
    const body = { tagName, tagType };
    const { data } = await api.put(`/tag/tag-modify/${tagId}`, body);
    return data;
}