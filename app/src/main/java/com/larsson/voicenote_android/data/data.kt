package com.larsson.voicenote_android.data

import java.util.*


data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var txtContent: String
)



var note1 = Note(getUUID(),"Title1", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note2 = Note("1","Title2", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note3 = Note("2","Title3", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note4 = Note("3","Title4", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note5 = Note("4","Title5", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note6 = Note("5","Title6", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note7 = Note("6","Title8", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note8 = Note("7","Title9", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note9 = Note("8","Title10", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")
var note10 = Note("9","Title11", "Lorem ipsum dolor sit amet consectetur. Sed odio sed dolor ac tempor facilisi et at blandit. Scelerisque metus duis dui sit sed ac. Placerat placerat tristique ac gravida odio volutpat dolor odio elementum. Aenean sed condimentum blandit auctor. Mauris tortor pellentesque dictumst amet diam sed. Gravida sem faucibus sit odio lacus elit faucibus amet vel. In enim tristique sed a tristique.")

fun getUUID() = UUID.randomUUID().toString()