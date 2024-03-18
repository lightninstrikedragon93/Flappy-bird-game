#version 330 core

layout(location = 0) out vec4 color;

in DATA{
    vec2 tc;
} fs_in;

uniform int top;
uniform sampler2D tex;

void main(){
    vec2 textCoord = fs_in.tc;

    if(top == 1)
    textCoord.y = 1.0 - textCoord.y;

    color = texture(tex, textCoord);
    if(color.w <1.0)
        discard;
}